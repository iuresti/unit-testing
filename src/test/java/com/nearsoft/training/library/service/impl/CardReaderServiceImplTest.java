package com.nearsoft.training.library.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.smartcardio.Card;

import org.junit.Test;

import com.nearsoft.training.library.config.CardReaderConfigurationProperties;
import com.nearsoft.training.library.model.User;

public class CardReaderServiceImplTest {

    @Test
    public void givenASuccessRequestAndANormalResponse_whenGetUser_ThenUserReturned() throws IOException {

        String curp = UUID.randomUUID().toString();
        byte[] response = ("ELNOMBRE|" + curp + "|20191115").getBytes();

        CardReaderConfigurationProperties cardReaderConfigurationProperties = getCardReaderConfigurationProperties();
        CardReaderServiceImpl cardReaderService = new CardReaderServiceImpl(cardReaderConfigurationProperties);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = new ByteArrayInputStream(response);

        // when:
        User user = cardReaderService.getUser(outputStream, inputStream);

        // Then:
        String request = outputStream.toString();

        assertThat(request).isEqualTo("AX0");

        assertThat(user.getName()).isEqualTo("ELNOMBRE");
        assertThat(user.getCurp()).isEqualTo(curp);
        assertThat(user.getValidityDate()).isEqualTo("20191115");

    }

    private CardReaderConfigurationProperties getCardReaderConfigurationProperties() {
        CardReaderConfigurationProperties cardReaderConfigurationProperties = new CardReaderConfigurationProperties();

        cardReaderConfigurationProperties.setHost("host");
        cardReaderConfigurationProperties.setPort(123);

        return cardReaderConfigurationProperties;
    }

}
