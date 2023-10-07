package com.example.demo.webs;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;






import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.ui.Model;



@Controller
public class HomeController {

 @Autowired
  private VehicleServiceRepository vehicleServiceRepository;


    @GetMapping("/")
    public String securedHome(@AuthenticationPrincipal OAuth2User user, Model model, Authentication authentication) {
        if (user != null) {
            String username = user.getAttribute("name"); // GitHub username
            String email = user.getAttribute("email"); // GitHub user's email
            String location = user.getAttribute("location"); 
            String name = user.getAttribute("given_name"); 
             String contact = user.getAttribute("contact_number"); 
             String values=user.getAttribute("sub"); 
             String pic=user.getAttribute("picture"); 
        if(location==null){
            location="null";

        }
if(contact==null){
    contact="null";
}
            // Add more attributes as needed from the GitHub user object
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("location", location);
             model.addAttribute("name", name);
              model.addAttribute("contact_number", contact);
               model.addAttribute("values", values);
                model.addAttribute("picture", pic);
            return "home"; // Return the view name
        } else {
            // Handle the case where user is null, possibly by redirecting to an error page or login page.
            return "redirect:/login";
        }
    }
    @PostMapping("/delete")
    public String deleteService(@RequestParam("id") Long id) {
        // Assuming your repository method for deleting by bookingId is called "deleteByBookingId"
        vehicleServiceRepository.deleteById(id);
        return "redirect:/services";// Redirect back to the service list page
    }
 @GetMapping("/service-list")
 public String diru(){
    return "service-list";
 }
  @GetMapping("/insert")
  public String showInsertForm() {
      return "insert"; // Return the HTML form page
  }

 
  @GetMapping("/services")
  @PreAuthorize("hasAuthority('SCOPE_openid') and hasAuthority('SCOPE_https://www.googleapis.com/auth/userinfo.email')")
  public String listServices(@AuthenticationPrincipal OAuth2User user, Model model, Authentication authentication) {
    String username = user.getAttribute("name"); 
      List<VehicleService> services = vehicleServiceRepository.findByUsername(username);
      model.addAttribute("services", services);
      return "service-list";
  }
  


@GetMapping("/services1")
  @PreAuthorize("hasAuthority('SCOPE_openid') and hasAuthority('SCOPE_https://www.googleapis.com/auth/userinfo.email')")
  public String listServices1(Model model) {
    
      List<VehicleService> services = vehicleServiceRepository.findAll();
      model.addAttribute("services1", services);
      return "service-list1";
  }


  


  @GetMapping("/add")
  @PreAuthorize("hasAuthority('SCOPE_openid') and hasAuthority('SCOPE_https://www.googleapis.com/auth/userinfo.email')")
  public String showForm(@AuthenticationPrincipal OAuth2User user, Model model, Authentication authentication) {
      model.addAttribute("service", new VehicleService());
       String username = user.getAttribute("name");
 model.addAttribute("username", username);
      return "service-form";
  }

  @PostMapping("/logs")
  @PreAuthorize("hasAuthority('SCOPE_openid') and hasAuthority('SCOPE_https://www.googleapis.com/auth/userinfo.email')")
  public String saveService(@ModelAttribute VehicleService service) {
      vehicleServiceRepository.save(service);
      return "redirect:/services";
  }



 /* */
   




  
}