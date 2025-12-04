package com.allMighty.business_logic_domain.ai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.embeddings.CreateEmbeddingResponse;
import com.openai.models.embeddings.EmbeddingCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingService {

/*    private final OpenAIClient client;

    public EmbeddingService(@Value("${OPENAI_API_KEY}") String apiKey) {
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    public List<Float> generateEmbedding(String text) {
        EmbeddingCreateParams params = EmbeddingCreateParams.builder()
                .model("text-embedding-3-small")
                .input(text)
                .build();

        CreateEmbeddingResponse createEmbeddingResponse = client.embeddings().create(params);
        return createEmbeddingResponse.data().get(0).embedding();
    }*/
}