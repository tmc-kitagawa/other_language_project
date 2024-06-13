import { useEffect, type FC, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useForm, SubmitHandler } from 'react-hook-form';
// 本番用
// import { searchHandler } from '../utils/searchHandler';

interface Source {
  title: string;
  link: string;
}

interface Article {
  query: string;
  sources?: Source[];
  searchResult?: string;
}

type Input = {
  query: string;
};

export const Search: FC = () => {
  const [articles, setArticles] = useState<Article[]>([]);
  const { register, handleSubmit } = useForm<Input>();

  const location = useLocation();

  // 開発用
  useEffect(() => {
    const initialArticle: Article = {
      query: location.state.query,
      // query:
      //   '2022年FIFAワールドカップの優勝国と活躍した選手について日本語で教えてください。',
      sources: [
        { title: '2022年FIFAワールドカップ', link: 'https://hoge.com/hoge' },
        { title: 'リオネル・メッシ', link: 'https://fuga.com/fuga' },
      ],
      searchResult:
        '2022年FIFAワールドカップの優勝国はアルゼンチンで、36年ぶり3回目の優勝を果たしました。大会最優秀選手に選ばれたのはリオネル・メッシです。',
    };
    setArticles((prev) => [...prev, initialArticle]);
  }, []);
  const onSubmit: SubmitHandler<Input> = async (data) => {
    console.log(data.query);
    setArticles((prev) => [
      ...prev,
      {
        query: data.query,
        // query: '今日と明日の天気を教えて',
        sources: [
          { title: '今日の天気', link: 'https://tenki' },
          { title: '明日の天気', link: 'https://tenki' },
        ],
        searchResult: '今日の天気は晴れです。明日の天気は雨です。',
      },
    ]);
  };

  // 本番用
  //   useEffect(() => {
  //     (async () => {
  //       setArticles(() => [{ query: location.state.query, searchResult: '' }]);
  //       await searchHandler({
  //         query: location.state.query,
  //         setArticles: setArticles,
  //       });
  //     })();
  //   }, []);
  //   const onSubmit: SubmitHandler<Input> = async (data) => {
  //     console.log(data.query);
  //     setArticles((prev) => [...prev, { query: data.query, searchResult: '' }]);
  //     await searchHandler({
  //       query: data.query,
  //       setArticles: setArticles,
  //     });
  //   };

  return (
    <>
      <div>
        {articles.map(({ query, sources, searchResult }: Article, i) => (
          <article key={i}>
            <h1>{query}</h1>
            {sources && (
              <>
                <p>ソース</p>
                <div>
                  {sources.map(({ title, link }, i) => (
                    <div key={i}>
                      <p>{title}</p>
                      <a href={link}>{link}</a>
                    </div>
                  ))}
                </div>
              </>
            )}
            {searchResult && (
              <>
                <p>回答</p>
                <p>{searchResult}</p>
              </>
            )}
          </article>
        ))}
      </div>
      <form onSubmit={handleSubmit(onSubmit)}>
        <textarea
          placeholder="何か質問してください"
          {...register('query')}
        ></textarea>
        <button>検索</button>
      </form>
    </>
  );
};
