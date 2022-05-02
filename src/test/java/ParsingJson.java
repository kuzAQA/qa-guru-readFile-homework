import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;

public class ParsingJson {
    @Test
    @DisplayName("Парсим JSON")
    void parseJson() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try(ZipFile zipFile = new ZipFile("src/test/resources/zip/json-sample.zip")) {
            ZipEntry entry = zipFile.getEntry("sample.json");
            String jsonString = new String(zipFile.getInputStream(entry).readAllBytes(), StandardCharsets.UTF_8);
            PeopleJSON people = objectMapper.readValue(jsonString, PeopleJSON.class);

            assertThat(people.getFirstName()).isEqualTo("Joe");
            assertThat(people.getLastName()).isEqualTo("Jackson");
            assertThat(people.getGender()).isEqualTo("male");
            assertThat(people.getAge() == 28).isTrue();
            assertThat(people.getNumber()).isEqualTo("7349282382");
        }
    }
}
