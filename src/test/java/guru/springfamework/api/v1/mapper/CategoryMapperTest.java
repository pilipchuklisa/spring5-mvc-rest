package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CategoryMapperTest {

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    public static final Long ID = 1L;
    public static final String NAME = "name";

    @Test
    public void categoryToCategoryDto() {
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDto(category);

        Assertions.assertEquals(category.getId(), categoryDTO.getId());
        Assertions.assertEquals(category.getName(), categoryDTO.getName());
    }

    @Test
    public void categoryDtoToCategory() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID);
        categoryDTO.setName(NAME);

        Category category = categoryMapper.categoryDtoToCategory(categoryDTO);

        Assertions.assertEquals(categoryDTO.getId(), category.getId());
        Assertions.assertEquals(categoryDTO.getName(), category.getName());
    }
}