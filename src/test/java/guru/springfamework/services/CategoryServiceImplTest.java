package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImplTest {

    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    public static final Long ID = 1L;
    public static final String NAME = "name";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
    }

    @Test
    public void getAllCategories() {
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        List<Category> categories = new ArrayList<>(1);
        categories.add(category);

        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        Assertions.assertEquals(categories.size(), categoryDTOS.size());
    }

    @Test
    public void getCategoryByName() {
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        Mockito.when(categoryRepository.findByName(NAME)).thenReturn(category);

        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        Assertions.assertEquals(ID, categoryDTO.getId());
        Assertions.assertEquals(NAME, categoryDTO.getName());
    }
}