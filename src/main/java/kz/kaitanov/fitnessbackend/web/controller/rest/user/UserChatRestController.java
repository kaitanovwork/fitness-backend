package kz.kaitanov.fitnessbackend.web.controller.rest.user;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.ChatMessage;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.security.Principal;
import java.util.Optional;


@Tag(name = "UserChatRestController", description = "Контроллер для чата пользователя с теренером")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class UserChatRestController {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    @Operation(summary = "Эндпоинт чата пользователя с тренером")
    @MessageMapping("/{username}/messages")
    public void messageProcess(@DestinationVariable String username,
                               @Payload ChatMessage chatMessage,
                               Principal principal) {
        chatMessage.setSenderName(principal.getName());
        messagingTemplate.convertAndSend("/channel/" + username, chatMessage);
    }

    /* Один из вариантов реализации

    @Operation(summary = "Эндпоинт чата пользователя с тренером")
    @MessageMapping("/{username}/messages")
    public void messageProcess1(@PathVariable String username,
                                @DestinationVariable @Positive Long coachId,
                                @Payload ChatMessage chatMessage,
                                Principal principal) {
        chatMessage.setSenderName(principal.getName());
        messagingTemplate.convertAndSendToUser(username,
                "/channel/" + coachId,
                chatMessage);
    }

     */
}
