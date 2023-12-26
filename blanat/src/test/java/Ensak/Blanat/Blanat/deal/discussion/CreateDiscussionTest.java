package Ensak.Blanat.Blanat.deal.discussion;
import Ensak.Blanat.Blanat.DTOs.discDTO.DiscussionDTO;
import Ensak.Blanat.Blanat.entities.Discussion;
import Ensak.Blanat.Blanat.entities.UserApp;
import Ensak.Blanat.Blanat.enums.Categories;
import Ensak.Blanat.Blanat.exeptions.UserNotFoundException;
import Ensak.Blanat.Blanat.repositories.DiscussionRepository;
import Ensak.Blanat.Blanat.repositories.UserRepository;
import Ensak.Blanat.Blanat.services.discuService.DiscussionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateDiscussionTest {

    @InjectMocks
    private DiscussionServiceImpl discussionService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DiscussionRepository discussionRepository;



    @Test
    public void testCreateDiscussion_UserExists() {
        // Simulate an existing user
        UserApp existingUser = new UserApp();
        existingUser.setUserName("nassima");
        when(userRepository.findByEmail("jebbarnassima12@gmail.com")).thenReturn(Optional.of(existingUser));

        // Mock the behavior of discussionRepository.save to return a non-null Discussion object
        when(discussionRepository.save(ArgumentMatchers.any(Discussion.class))).thenAnswer(invocation -> {
            Discussion argument = invocation.getArgument(0);
            argument.setId(13L); // Set an ID to simulate successful save
            return argument;
        });
        // Create a valid DiscussionDTO
        DiscussionDTO discussionDTO = new DiscussionDTO();
        discussionDTO.setId(13L);
        discussionDTO.setTitre("gaga");
        discussionDTO.setDescription("gaga");
        discussionDTO.setCategories(Categories.TRAVEL); // Assuming Categories.TRAVEL is a valid enum value
        discussionDTO.setCreateurUsername("nassima");

        // Call the method and verify the result
        DiscussionDTO result = discussionService.createDiscussion(discussionDTO, "jebbarnassima12@gmail.com");
        assertNotNull(result);
        assertEquals("gaga", result.getTitre());
        assertEquals("gaga", result.getDescription());
        assertEquals("nassima", result.getCreateurUsername());
    }

    @Test
    public void testCreateDiscussionWithNonExistingUser() {
        String userEmail = "usernotexist@example.com";
        DiscussionDTO discussionDTO = new DiscussionDTO();
        discussionDTO.setId(14L);
        discussionDTO.setTitre("titre1");
        discussionDTO.setDescription("description de cette discussion");
        discussionDTO.setCategories(Categories.FASHION);


        assertThrows(UserNotFoundException.class, () -> discussionService.createDiscussion(discussionDTO, userEmail));
    }


}

