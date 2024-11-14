package com.jeommechu.food.controller

import com.jeommechu.food.dto.FoodResponseDto
import com.jeommechu.food.entity.FoodCategory
import com.jeommechu.food.service.FoodService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class FoodControllerTest {

 @Autowired
 private lateinit var mockMvc: MockMvc

 @MockBean
 private lateinit var foodService: FoodService

 @Test
 fun `should return food response and generate REST Docs`() {
  // given
  val category = listOf("한식", "양식")
  val foodResponse = FoodResponseDto(name = "Bibimbap", category = FoodCategory.한식)

  Mockito.`when`(foodService.getRandomFood(Mockito.anyList())).thenReturn(foodResponse)

  // when, then
  mockMvc.perform(RestDocumentationRequestBuilders.get("/api/food")
   .param("category", *category.toTypedArray()) // 쿼리 파라미터로 category 전달
   .accept(MediaType.APPLICATION_JSON))
   .andExpect(status().isOk)
   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
   .andExpect(jsonPath("$.name").value("Bibimbap"))
   .andExpect(jsonPath("$.category").value("한식"))
   .andDo(MockMvcRestDocumentation.document(
    "food-get",  // 문서화 파일 이름
    queryParameters(
     parameterWithName("category").description("The categories of food to filter (e.g. 한식, 양식)")
    ),
    responseFields(
     fieldWithPath("name").description("The name of the food"),
     fieldWithPath("category").description("The category of the food")
    )
   ))
 }
}