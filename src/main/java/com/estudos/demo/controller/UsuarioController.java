package com.estudos.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estudos.demo.modelo.Papel;
import com.estudos.demo.modelo.Usuario;
import com.estudos.demo.service.PapelService;
import com.estudos.demo.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private PapelService papelService;

    @Autowired
    private UsuarioService usuarioService;

    private boolean temAutorizacao(Usuario usuario, String papel) {
        for (Papel pp : usuario.getPapeis()) {
            if (pp.getPapel().equals(papel)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/index")
    public String index(@CurrentSecurityContext(expression = "authentication.name") String login) {

        Usuario usuario = usuarioService.buscarUsuarioPorLogin(login);

        String redirectURL = "";
        if (temAutorizacao(usuario, "ADMIN")) {
            redirectURL = "/auth/admin/admin-index";
        } else if (temAutorizacao(usuario, "USER")) {
            redirectURL = "/auth/user/user-index";
        } else if (temAutorizacao(usuario, "BIBLIOTECARIO")) {
            redirectURL = "/auth/biblio/biblio-index";
        }
        return redirectURL;
    }

    @GetMapping("/novo")
    public String adicionarUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "/publica-criar-usuario";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@Valid Usuario usuario, BindingResult result,
            RedirectAttributes attributes, Model model) {

        if (result.hasErrors()) {
            return "/publica-criar-usuario";
        }

        Usuario usr = usuarioService.buscarUsuarioPorLogin(usuario.getLogin());
        if (usr != null) {
            model.addAttribute("loginExiste", "user.controller.already");
            return "/publica-criar-usuario";
        }

        usuarioService.gravarUsuario(usuario);

        attributes.addFlashAttribute("mensagem", "user.controller.saved");
        return "redirect:/usuario/novo";
    }

    @RequestMapping("/admin/listar")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarUsuarios(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("usuarios", usuarios);
        return "/auth/admin/admin-listar-usuario";
    }

    @GetMapping("/admin/apagar/{id}")
    public String apagarUsuario(Model model, @PathVariable("id") Long id) {
        usuarioService.apagarUsuarioPorId(id);
        return "redirect:/usuario/admin/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(Model model, @PathVariable("id") Long id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        return "/auth/user/user-editar-usuario";
    }

    @PostMapping("/editar/{id}")
    public String editarUsuario(@Valid Usuario usuario, BindingResult result,
            @PathVariable("id") long id) {
        if (result.hasErrors()) {
            usuario.setId(id);
            return "/auth/user/user-editar-usuario";
        }

        usuarioService.alterarUsuario(usuario);
        return "redirect:/usuario/admin/listar";
    }

    @GetMapping("/editarPapel/{id}")
    public String selecionarPapel(Model model, @PathVariable("id") Long id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        model.addAttribute("usuario", usuario);

        model.addAttribute("listaPapeis", papelService.listarPapel());

        return "/auth/admin/admin-editar-papel-usuario";
    }

    @PostMapping("/editarPapel/{id}")
    public String editarPapel(@PathVariable("id") long idUsuario,
            @RequestParam(value = "pps", required = false) int[] pps,
            Usuario usuario, RedirectAttributes attributes) {

        if (pps == null) {
            usuario.setId(idUsuario);
            attributes.addFlashAttribute("mensagem", "user.controller.role");
            return "redirect:/usuario/admin/listar";
        } else {
            usuarioService.atribuirPapelUsuario(idUsuario, pps, usuario.isAtivo());
        }
        return "redirect:/usuario/admin/listar";
    }
}
