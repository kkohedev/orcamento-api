package com.github.kkohedev.orcamento_api.config;

import com.github.kkohedev.orcamento_api.service.TransacaoTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel, TransacaoTools transacaoTools) {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem("""
                        Você é um assistente financeiro. Sua função é entender comandos de voz
                        do usuário sobre transações financeiras (gastos e receitas) e executar
                        a ação correta usando as ferramentas disponíveis. Seja direto e claro
                        nas respostas, como se estivesse falando com a pessoa.
                        """)
                .defaultTools(transacaoTools)
                .build();
    }
}