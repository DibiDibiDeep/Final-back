package com.example.finalproj;

import com.example.finalproj.dao.MemberDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Enumeration;
import java.util.Map;

@Controller
public class FinalController {

    @Autowired
    private MemberDao memberDao;

    @RequestMapping("/gologin")
    public String goLogin() {
        return "gologin";
    }

    @RequestMapping("/memberList")
    public String memberList(Model model, HttpSession session) {
        System.out.println("memberList 메서드 시작");
        System.out.println("세션 ID: " + session.getId());

        String userEmail = null;
        String userName = null;

        // OAuth2AuthenticationToken에서 사용자 정보 추출
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof OAuth2User) {
            Map<String, Object> attributes = ((OAuth2User) principal).getAttributes();
            userEmail = (String) attributes.get("email");
            userName = (String) attributes.get("name");
        }

        System.out.println("OAuth2User에서 가져온 userEmail: " + userEmail);

        if (userEmail != null) {
            System.out.println("로그인된 Google 계정: " + userEmail);
            model.addAttribute("userEmail", userEmail);
            model.addAttribute("userName", userName);
        } else {
            System.out.println("로그인된 Google 계정이 없습니다.");
            System.out.println("세션 속성들:");
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                System.out.println(attributeName + ": " + session.getAttribute(attributeName));
            }
        }

        model.addAttribute("mtdMemberList", memberDao.mtdMemberList());
        return "memberList";
    }

    @RequestMapping("/memberJoin")
    public String memberJoinPage() {
        return "memberJoin";
    }

    @RequestMapping("/memberDelete")
    public String memberDelete(HttpServletRequest req, Model model) {
        try {
            req.setCharacterEncoding("UTF-8");
            String userCodeStr = req.getParameter("USER_CODE");
            if (userCodeStr == null || userCodeStr.isEmpty()) {
                throw new IllegalArgumentException("USER_CODE가 제공되지 않았습니다.");
            }
            int USER_CODE = Integer.parseInt(userCodeStr);
            int result = memberDao.mtdMemberDelete(USER_CODE);
            if (result > 0) {
                return "redirect:/memberList";
            } else {
                model.addAttribute("error", "회원 삭제에 실패했습니다.");
                return "memberList";
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "잘못된 USER_CODE 형식입니다.");
            return "memberList";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "회원 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return "memberList";
        }
    }
}