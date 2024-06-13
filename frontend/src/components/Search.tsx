import { useEffect, type FC, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useForm, SubmitHandler } from 'react-hook-form';
import './Search.scss';
import TextareaAutosize from 'react-textarea-autosize';
// 本番用
import { searchHandler } from '../utils/searchHandler';

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
  const { register, handleSubmit, reset } = useForm<Input>();

  const location = useLocation();

  // 開発用
  //   useEffect(() => {
  //     const initialArticle: Article = {
  //       query: location.state.query,
  //       // query:
  //       //   '2022年FIFAワールドカップの優勝国と活躍した選手について日本語で教えてください。',
  //       sources: [
  //         { title: '2022年FIFAワールドカップ', link: 'https://hoge.com/hoge' },
  //         { title: 'リオネル・メッシ', link: 'https://fuga.com/fuga' },
  //       ],
  //       searchResult:
  //         '2022年FIFAワールドカップの優勝国はアルゼンチンで、36年ぶり3回目の優勝を果たしました。大会最優秀選手に選ばれたのはリオネル・メッシです。',
  //     };
  //     setArticles((prev) => [...prev, initialArticle]);
  //   }, []);
  //   const onSubmit: SubmitHandler<Input> = async (data) => {
  //     console.log(data.query);
  //     setArticles((prev) => [
  //       ...prev,
  //       {
  //         query: data.query,
  //         // query: '今日と明日の天気を教えて',
  //         sources: [
  //           { title: '今日の天気', link: 'https://tenki' },
  //           { title: '明日の天気', link: 'https://tenki' },
  //         ],
  //         searchResult: '今日の天気は晴れです。明日の天気は雨です。',
  //       },
  //     ]);
  //     reset();
  //   };

  //   本番用
  useEffect(() => {
    (async () => {
      setArticles(() => [{ query: location.state.query, searchResult: '' }]);
      await searchHandler({
        query: location.state.query,
        setArticles: setArticles,
      });
    })();
  }, []);
  const onSubmit: SubmitHandler<Input> = async (data) => {
    console.log(data.query);
    setArticles((prev) => [...prev, { query: data.query, searchResult: '' }]);
    await searchHandler({
      query: data.query,
      setArticles: setArticles,
    });
    reset();
  };

  return (
    <>
      <div className="search-container">
        {articles.map(({ query, sources, searchResult }: Article, i) => (
          <article className="article-container" key={i}>
            <h1>{query}</h1>
            {sources && (
              <>
                <p>ソース</p>
                <div className="source-container">
                  {sources.map(({ title, link }, i) => (
                    <div className="source" key={i}>
                      <p>{title}</p>
                      <a href={link} target="_brank">
                        外部リンク
                        {/* {link} */}
                      </a>
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
      <footer>
        <form className="search-form" onSubmit={handleSubmit(onSubmit)}>
          <div className="textarea-container">
            <TextareaAutosize
              className="textarea"
              minRows={2}
              placeholder="何か質問してください"
              {...register('query')}
            />
          </div>
          <button>
            <img src="/send.svg" alt="送信" />
          </button>
        </form>
      </footer>
    </>
  );
};
