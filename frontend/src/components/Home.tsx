import type { FC } from 'react';
import { useNavigate } from 'react-router-dom';
import { useForm, SubmitHandler } from 'react-hook-form';

type Input = {
  query: string;
};

export const Home: FC = () => {
  const navigate = useNavigate();
  const { register, handleSubmit } = useForm<Input>();

  const onSubmit: SubmitHandler<Input> = (data) => {
    navigate('/search', {
      state: { query: data.query },
    });
  };

  const logoutHandler = () => {
    //ここでセッショントークンを削除
  };

  return (
    <>
      <div>
        <h1>何か質問してください</h1>
        <form onSubmit={handleSubmit(onSubmit)}>
          <textarea
            placeholder="何か質問してください"
            {...register('query')}
          ></textarea>
          <button>検索</button>
        </form>
        <button onClick={logoutHandler}>
          <a href="/login">ログアウト</a>
        </button>
      </div>
    </>
  );
};
