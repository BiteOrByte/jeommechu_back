package com.jeommechu.user.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jeommechu.user.dto.NicknameDto
import org.springframework.stereotype.Service
import java.io.InputStream
import java.util.*

@Service
class NicknameGeneratorService {

    private val adjectives: List<String>
    private val nouns: List<String>
    private val random = Random()

    init {
        // JSON 파일에서 형용사와 명사 목록 로드
        val nicknameData = loadNicknameData()
        adjectives = nicknameData.adjectives
        nouns = nicknameData.nouns
    }

    /**
     * JSON 파일에서 형용사와 명사 데이터를 읽어온다.
     * @return NicknameDto: 형용사와 명사 목록이 포함된 데이터 클래스
     * @throws IOException: 파일을 읽는 중 오류가 발생할 경우
     */
    private fun loadNicknameData(): NicknameDto {
        val objectMapper = ObjectMapper()
        val inputStream: InputStream = this::class.java.getResourceAsStream("/nicknames.json")
        return objectMapper.readValue(inputStream, NicknameDto::class.java)
    }

    /**
     * 랜덤 닉네임 생성
     * 형용사와 명사를 랜덤으로 조합하여 반환하여 생성한다.
     * @return String: 생성된 랜덤 닉네임
     */
    fun generateNickname(): String {
        return "${getRandomElement(adjectives)} ${getRandomElement(nouns)}"
    }

    /**
     * 주어진 리스트에서 랜덤으로 요소를 선택한다.
     * @param list: 선택할 요소가 포함된 리스트
     * @return T: 랜덤으로 선택된 요소
     */
    private fun <T> getRandomElement(list: List<T>): T {
        return list[random.nextInt(list.size)]
    }
}
