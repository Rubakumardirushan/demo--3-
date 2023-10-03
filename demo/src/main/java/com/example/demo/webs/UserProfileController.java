package com.example.demo.webs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserProfileController {
Long id;
    @Autowired
    private UserProfileRepository userProfileRepository;

    @GetMapping("/")
    public String securedHome(@AuthenticationPrincipal OAuth2User user, Model model) {
        if (user != null) {
            String username = user.getAttribute("login"); // GitHub username
            String email = user.getAttribute("email"); // GitHub user's email
            String location = user.getAttribute("location"); 
            id=user.getAttribute("Name"); 
            if(email == null){
                email = "email is private";
            }
            if(username == null){
                username = user.getAttribute("name"); 
            }
            // Add more attributes as needed from the GitHub user object
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("location", location);
            return "home"; // Return the view name
        } else {
            // Handle the case where user is null, possibly by redirecting to an error page or login page.
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userProfile", new UserProfile());
        return "registration-form";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserProfile userProfile) {
        userProfileRepository.save(userProfile);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        UserProfile userProfile = userProfileRepository.findById(11L).orElse(new UserProfile());
        model.addAttribute("userProfile", userProfile);
        return "profile";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model) {
        UserProfile userProfile = userProfileRepository.findById(11L).orElse(new UserProfile());
        model.addAttribute("userProfile", userProfile);
        return "edit-profile";
    }

    @PostMapping("/edit")
    public String editUserProfile(@ModelAttribute UserProfile userProfile, RedirectAttributes redirectAttributes) {
        userProfileRepository.save(userProfile);
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully.");
        return "redirect:/profile";
    }
}
