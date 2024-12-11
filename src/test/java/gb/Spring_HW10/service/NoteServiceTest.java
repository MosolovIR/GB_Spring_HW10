package gb.Spring_HW10.service;

import gb.Spring_HW10.entity.Note;
import gb.Spring_HW10.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllNotesTest() {
        Note note = new Note();
        note.setTitle("Test Title");
        note.setContent("Test Content");

        List<Note> expectedNotes = Collections.singletonList(note);
        Mockito.when(noteRepository.findAll()).thenReturn(expectedNotes);
        List<Note> actualNotes = noteService.getAllNotes();

        assertEquals(expectedNotes, actualNotes);
    }

    @Test
    public void getNoteByIdTest() {
        Long id = 1L;
        Note note = new Note();
        note.setId(id);
        note.setTitle("Test Title");
        note.setContent("Test Content");

        Mockito.when(noteRepository.findById(id)).thenReturn(Optional.of(note));
        Note actualNote = noteService.getNoteById(id);
        assertEquals(note, actualNote);

        Mockito.when(noteRepository.findById(2L)).thenReturn(Optional.empty());
        Note actualNoteNotFound = noteService.getNoteById(2L);
        assertNull(actualNoteNotFound);
    }

    @Test
    public void saveOrUpdateNoteTest() {
        Note note = new Note();
        note.setTitle("Test Title");
        note.setContent("Test Content");

        Mockito.when(noteRepository.save(note)).thenReturn(note);

        Note savedNote = noteService.saveOrUpdateNote(note);
        assertEquals(note, savedNote);

        Mockito.verify(noteRepository, Mockito.times(1)).save(note);
    }

    @Test
    public void deleteNoteTest() {
        Long id = 1L;
        noteService.deleteNote(id);
        Mockito.verify(noteRepository, Mockito.times(1)).deleteById(id);
    }
}
