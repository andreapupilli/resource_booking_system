package it.unicam.resourcebooking.web;

import it.unicam.resourcebooking.dto.CreateBookingRequest;
import it.unicam.resourcebooking.dto.CreateUserRequest;
import it.unicam.resourcebooking.model.Resource;
import it.unicam.resourcebooking.model.ResourceType;
import it.unicam.resourcebooking.service.BookingService;
import it.unicam.resourcebooking.service.ResourceService;
import it.unicam.resourcebooking.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.*;

@Controller
public class WebController {

    private final ResourceService resourceService;
    private final UserService userService;
    private final BookingService bookingService;

    public WebController(ResourceService resourceService, UserService userService, BookingService bookingService) {
        this.resourceService = resourceService;
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String home(Model model,
                       @ModelAttribute("uiError") String uiError,
                       @ModelAttribute("uiInfo") String uiInfo) {

        model.addAttribute("resources", resourceService.list());
        model.addAttribute("users", userService.list());
        model.addAttribute("bookings", bookingService.list());
        model.addAttribute("resourceTypes", ResourceType.values());

        model.addAttribute("newResource", new Resource());
        model.addAttribute("newBooking", new BookingForm());
        model.addAttribute("newUser", new CreateUserForm());

        if (uiError != null && !uiError.isBlank()) model.addAttribute("uiError", uiError);
        if (uiInfo != null && !uiInfo.isBlank()) model.addAttribute("uiInfo", uiInfo);

        return "index";
    }

    @PostMapping("/ui/resources")
    public String createResource(@ModelAttribute("newResource") Resource resource,
                                 RedirectAttributes redirectAttributes) {
        try {
            resource.setActive(true);
            resourceService.create(resource);
            redirectAttributes.addFlashAttribute("uiInfo", "Risorsa creata correttamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("uiError", "Errore creazione risorsa: " + safeMessage(ex));
        }
        return "redirect:/";
    }


    @PostMapping("/ui/bookings")
    public String createBooking(@ModelAttribute("newBooking") BookingForm form,
                                RedirectAttributes redirectAttributes) {
        try {
            if (form.getResourceId() == null || form.getUserId() == null) {
                redirectAttributes.addFlashAttribute("uiError", "Seleziona sia una risorsa sia un utente.");
                return "redirect:/";
            }
            if (form.getStartAt() == null || form.getEndAt() == null) {
                redirectAttributes.addFlashAttribute("uiError", "Inserisci sia data/ora inizio sia data/ora fine.");
                return "redirect:/";
            }
            if (!form.getEndAt().isAfter(form.getStartAt())) {
                redirectAttributes.addFlashAttribute("uiError", "La fine deve essere successiva all'inizio.");
                return "redirect:/";
            }

            ZoneId zone = ZoneId.systemDefault();
            OffsetDateTime startAt = form.getStartAt().atZone(zone).toOffsetDateTime();
            OffsetDateTime endAt = form.getEndAt().atZone(zone).toOffsetDateTime();

            bookingService.create(
                    new CreateBookingRequest(
                            form.getResourceId(),
                            form.getUserId(),
                            startAt,
                            endAt
                    )
            );

            redirectAttributes.addFlashAttribute("uiInfo", "Prenotazione creata correttamente.");
        }
        catch (it.unicam.resourcebooking.exception.ApiException ex) {

            if (ex.getStatus() == org.springframework.http.HttpStatus.CONFLICT) {

                switch (ex.getMessage()) {
                    case "RESOURCE_UNAVAILABLE" -> redirectAttributes.addFlashAttribute(
                            "uiWarning",
                            "Prenotazione NON effettuata: la risorsa è già prenotata nell'intervallo selezionato."
                    );

                    case "RESOURCE_INACTIVE" -> redirectAttributes.addFlashAttribute(
                            "uiWarning",
                            "Prenotazione NON effettuata: la risorsa non è attiva e non può essere prenotata."
                    );

                    default -> redirectAttributes.addFlashAttribute(
                            "uiWarning",
                            "Prenotazione NON effettuata: conflitto sulla risorsa."
                    );
                }
            } else {
                redirectAttributes.addFlashAttribute(
                        "uiError",
                        "Errore creazione prenotazione: " + safeMessage(ex)
                );
            }
        }
        catch (Exception ex) {
            redirectAttributes.addFlashAttribute(
                    "uiError",
                    "Errore creazione prenotazione: " + safeMessage(ex)
            );
        }

        return "redirect:/";
    }


    @PostMapping("/ui/bookings/{id}/cancel")
    public String cancelBooking(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            bookingService.cancel(id);
            redirectAttributes.addFlashAttribute("uiInfo", "Prenotazione annullata.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("uiError", "Errore annullamento prenotazione: " + safeMessage(ex));
        }
        return "redirect:/";
    }

    @PostMapping("/ui/users")
    public String createUser(@ModelAttribute("newUser") CreateUserForm form,
                             RedirectAttributes redirectAttributes) {
        try {
            String username = (form.getUsername() == null) ? "" : form.getUsername().trim();
            if (username.isBlank()) {
                redirectAttributes.addFlashAttribute("uiError", "Inserisci un username.");
                return "redirect:/";
            }

            userService.create(new CreateUserRequest(username));

            redirectAttributes.addFlashAttribute("uiInfo", "Utente creato correttamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("uiError", "Impossibile creare utente: " + safeMessage(ex));
        }
        return "redirect:/";
    }

    private String safeMessage(Exception ex) {
        String msg = ex.getMessage();
        if (msg == null || msg.isBlank()) return "errore imprevisto.";
        if (msg.length() > 200) return msg.substring(0, 200) + "...";
        return msg;
    }

    public static class BookingForm {
        private Long resourceId;
        private Long userId;

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime startAt;

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime endAt;

        public Long getResourceId() { return resourceId; }
        public void setResourceId(Long resourceId) { this.resourceId = resourceId; }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public LocalDateTime getStartAt() { return startAt; }
        public void setStartAt(LocalDateTime startAt) { this.startAt = startAt; }

        public LocalDateTime getEndAt() { return endAt; }
        public void setEndAt(LocalDateTime endAt) { this.endAt = endAt; }
    }

    public static class CreateUserForm {
        private String username;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
    }
}

