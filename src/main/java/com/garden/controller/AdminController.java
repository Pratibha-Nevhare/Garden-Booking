package com.garden.controller;

import com.garden.dao.GCustomerRepository;
import com.garden.entities.G_customer;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {

    private final GCustomerRepository gCustomerRepository;

    @Autowired
    public AdminController(GCustomerRepository gCustomerRepository) {
        this.gCustomerRepository = gCustomerRepository;
    }

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model, HttpSession session) {
   
        if (session.getAttribute("loggedIn") == null || !(boolean) session.getAttribute("loggedIn")) {
     
            return "redirect:/admin/login";
        }

        // Get weekly counts and their start and end dates
        Map<Week, Integer> weeklyCounts = getWeeklyCounts();
       
        Map.Entry<Month, Integer> maxCountMonthEntry = getMonthOfMaxCount(weeklyCounts);

        // Add data to the model
        model.addAttribute("weeklyCounts", weeklyCounts);
        model.addAttribute("maxCountMonth", maxCountMonthEntry.getValue());
        model.addAttribute("maxCountMonthStartDate", maxCountMonthEntry.getKey().getStartDate());
        model.addAttribute("maxCountMonthEndDate", maxCountMonthEntry.getKey().getEndDate());

        return "admin-dashboard";
    }

    @GetMapping("/admin/login")
    public String showAdminLogin() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
       
        if ("admin".equals(username) && "ADMIN".equals(password)) {
          
            session.setAttribute("loggedIn", true);
           
            return "redirect:/admin/dashboard";
        } else {
            
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/admin/login";
        }
    }

    // Method to retrieve weekly counts and their start and end dates
    private Map<Week, Integer> getWeeklyCounts() {
        List<G_customer> bookings = gCustomerRepository.findAll();
        Map<Week, Integer> weeklyCounts = new HashMap<>();
        for (G_customer booking : bookings) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(booking.getCreationDate());
            int week = calendar.get(Calendar.WEEK_OF_YEAR);
            Week currentWeek = new Week(week, calendar);
            weeklyCounts.put(currentWeek, weeklyCounts.getOrDefault(currentWeek, 0) + 1);
        }
        return weeklyCounts;
    }

    // Method to retrieve the month of maximum count and its start and end dates
    private Map.Entry<Month, Integer> getMonthOfMaxCount(Map<Week, Integer> weeklyCounts) {
        Map<Integer, Integer> monthCounts = new HashMap<>();
        for (Map.Entry<Week, Integer> entry : weeklyCounts.entrySet()) {
            int month = entry.getKey().getMonth();
            monthCounts.put(month, monthCounts.getOrDefault(month, 0) + entry.getValue());
        }
        int maxMonthCount = 0;
        int maxMonth = 0;
        for (Map.Entry<Integer, Integer> entry : monthCounts.entrySet()) {
            if (entry.getValue() > maxMonthCount) {
                maxMonthCount = entry.getValue();
                maxMonth = entry.getKey();
            }
        }
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.YEAR, weeklyCounts.keySet().iterator().next().getYear());
        startDate.set(Calendar.MONTH, maxMonth - 1);
        startDate.set(Calendar.DAY_OF_MONTH, 1);
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.MONTH, 1);
        endDate.add(Calendar.DAY_OF_MONTH, -1);
        Month maxCountMonth = new Month(maxMonth, startDate, endDate);
        return new AbstractMap.SimpleEntry<>(maxCountMonth, maxMonthCount);
    }

    
    private static class Week {
        private final int week;
        private final Calendar startDate;
        private final Calendar endDate;

        public Week(int week, Calendar startDate) {
            this.week = week;
            this.startDate = (Calendar) startDate.clone();
            this.startDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            this.startDate.set(Calendar.WEEK_OF_YEAR, week);
            this.startDate.set(Calendar.HOUR_OF_DAY, 0);
            this.startDate.set(Calendar.MINUTE, 0);
            this.startDate.set(Calendar.SECOND, 0);
            this.startDate.set(Calendar.MILLISECOND, 0);
            this.endDate = (Calendar) this.startDate.clone();
            this.endDate.add(Calendar.DATE, 6);
        }

        public int getMonth() {
            return startDate.get(Calendar.MONTH) + 1;
        }

        public int getYear() {
            return startDate.get(Calendar.YEAR);
        }

        public String getStartDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(startDate.getTime());
        }

        public String getEndDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(endDate.getTime());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Week week1 = (Week) o;
            return week == week1.week &&
                    Objects.equals(startDate, week1.startDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(week, startDate);
        }
    }

    // Inner class representing a month with its start and end dates
    private static class Month {
        private final int month;
        private final Calendar startDate;
        private final Calendar endDate;

        public Month(int month, Calendar startDate, Calendar endDate) {
            this.month = month;
            this.startDate = (Calendar) startDate.clone();
            this.endDate = (Calendar) endDate.clone();
        }

        public int getMonth() {
            return month;
        }

        public String getStartDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(startDate.getTime());
        }

        public String getEndDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(endDate.getTime());
        }
    }
}
