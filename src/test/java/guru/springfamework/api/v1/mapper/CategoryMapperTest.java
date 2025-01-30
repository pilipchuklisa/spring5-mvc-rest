package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

public class CategoryMapperTest {

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    public static final Long ID = 1L;
    public static final String NAME = "name";

    @Test
    public void categoryToCategoryDto() {
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        CategoryDTO categoryDto = categoryMapper.categoryToCategoryDto(category);

        Assertions.assertEquals(category.getId(), categoryDto.getId());
        Assertions.assertEquals(category.getName(), categoryDto.getName());
    }

    @Test
    public void categoryDtoToCategory() {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setId(ID);
        categoryDto.setName(NAME);

        Category category = categoryMapper.categoryDtoToCategory(categoryDto);

        Assertions.assertEquals(categoryDto.getId(), category.getId());
        Assertions.assertEquals(categoryDto.getName(), category.getName());
    }
}