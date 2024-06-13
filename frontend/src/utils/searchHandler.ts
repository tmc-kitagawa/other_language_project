import { DynamicTool } from 'langchain/tools';
import { ChatOpenAI } from '@langchain/openai';
import type { ChatPromptTemplate } from '@langchain/core/prompts';
import { pull } from 'langchain/hub';
import { createOpenAIFunctionsAgent } from 'langchain/agents';
import { AgentExecutor } from 'langchain/agents';
import axios from 'axios';

interface Source {
  title: string;
  link: string;
}

interface Article {
  query: string;
  sources?: Source[];
  searchResult?: string;
}

interface Params {
  query: string;
  setArticles: React.Dispatch<React.SetStateAction<Article[]>>;
}

// 検索用
export const searchHandler = async ({ query, setArticles }: Params) => {
  const llm = new ChatOpenAI({
    model: 'gpt-3.5-turbo',
    temperature: 0,
    apiKey: import.meta.env.VITE_OPENAI_API_KEY,
  });
  const prompt = await pull<ChatPromptTemplate>(
    'hwchase17/openai-functions-agent'
  );
  const tools = [
    new DynamicTool({
      name: 'GoogleSearch',
      description:
        '最新の話題について答える場合に利用することができます。入力は検索内容です。',
      func: async (query: string) => {
        const path = `https://www.googleapis.com/customsearch/v1?key=${
          import.meta.env.VITE_GOOGLE_API_KEY
        }&cx=${
          import.meta.env.VITE_GOOGLE_CUSTOM_SEARCH_ID
        }&q=${query}&num=2&cr=countryJP`;
        const data = await axios(path).then((res) => res.data);
        setArticles((prev) => {
          const res = [...prev];
          res[res.length - 1].sources = data.items.map(
            ({ title, link }: Source) => ({ title, link })
          );
          return res;
        });
        console.log(data.items);
        return (
          'title, snippet\n' +
          data.items
            .map(
              ({ title, snippet }: { title: string; snippet: string }) =>
                `${title},${snippet}`
            )
            .join('\n')
        );
      },
    }),
  ];
  const agent = await createOpenAIFunctionsAgent({
    llm,
    tools,
    prompt,
  });
  const agentExecutor = new AgentExecutor({
    agent,
    tools,
  });
  const logStream = await agentExecutor.streamLog({
    input: query,
  });
  for await (const chunk of logStream) {
    if (chunk.ops?.length > 0 && chunk.ops[0].op === 'add') {
      const addOp = chunk.ops[0];
      if (
        addOp.path.startsWith('/logs/ChatOpenAI') &&
        typeof addOp.value === 'string' &&
        addOp.value.length
      ) {
        setArticles((prev) => {
          const res = [...prev];
          res[res.length - 1].searchResult += addOp.value;
          return res;
        });
        console.log(addOp.value);
      }
    }
  }
};
