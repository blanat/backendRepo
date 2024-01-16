package Ensak.Blanat.Blanat.deal.discussion;
import Ensak.Blanat.Blanat.DTOs.discDTO.DiscussionDTO;
import Ensak.Blanat.Blanat.DTOs.discDTO.MessageDTO;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.repositories.DiscussionRepository;
import Ensak.Blanat.Blanat.repositories.DiscussionViewRepository;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import Ensak.Blanat.Blanat.services.authServices.JwtService;
import Ensak.Blanat.Blanat.services.authServices.UserService;
import Ensak.Blanat.Blanat.services.discuService.DiscussionServiceImpl;
import Ensak.Blanat.Blanat.services.imagesDealService.imageUrlBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import Ensak.Blanat.Blanat.exeptions.AuthenticationException;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiscussionServiceTest {

    @InjectMocks
    private DiscussionServiceImpl discussionService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private DiscussionRepository discussionRepository;
    @Mock
    private JwtService jwtTokenService;
    @Mock
    private UserService userService;
    @Mock
    private DiscussionViewRepository discussionViewRepository;

    @Mock
    private imageUrlBuilder imageBuilder;
    @Mock
    private Authentication authentication;
    //Case : la discussion n'existe pas :

    @Test
    @ExtendWith(MockitoExtension.class)
    public void testAddMessageWithInvalidDiscussion() {
        Long invalidDiscussionId = 999L;
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent("Test message content");

        when(discussionRepository.findById(invalidDiscussionId)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> discussionService.addMessage(invalidDiscussionId, messageDTO));
    }

    //case : l'utilisateur n'est pas authentifié :

    @Test
    @ExtendWith(MockitoExtension.class)
    public void testAddMessageWithUnauthenticatedUser() {
        Long discussionId = 1L;
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent("Test message content");

        Discussion existingDiscussion = new Discussion();
        existingDiscussion.setId(discussionId);

        when(discussionRepository.findById(discussionId)).thenReturn(Optional.of(existingDiscussion));

        assertThrows(AuthenticationException.class, () -> discussionService.addMessage(discussionId, messageDTO));
    }





    @Test
    public void testGetAllDiscussionsInfo_NoDiscussions() {
        when(discussionRepository.findAll()).thenReturn(new ArrayList<>());

        List<DiscussionDTO> discussions = discussionService.getAllDiscussionsInfo();

        assertTrue(discussions.isEmpty());
    }
    @Test
    public void testGetAllDiscussionsInfo_EmptyList() {
        // Mock du repository pour retourner une liste vide
        when(discussionRepository.findAll()).thenReturn(Collections.emptyList());

        // Appel de la méthode à tester
        List<DiscussionDTO> discussions = discussionService.getAllDiscussionsInfo();

        assertTrue(discussions.isEmpty()); // Vérifie si la liste retournée est vide
    }



    private static final Long DISCUSSION_ID = 1L;
    private static final String VALID_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqZWJiYXJuYXNzaW1hMTJAZ21haWwuY29tIiwiaWF0IjoxNzAzMzU5MjU1LCJleHAiOjE3MDMzNjI4NTV9.C67Xe9uOdfy9ZD2FfnKXdW3pwfwgVs_JAfNKJg_tviw";
    private static final String INVALID_TOKEN = "Invalid token";





}
