import type { FC } from 'react';
import { Link } from 'react-router-dom';

export const Home: FC = () => {
  const logoutHandler = () => {
    //ここでセッショントークンを削除
  };

  return (
    <>
      <div>
        <p>Homeページ</p>
        <Link to="/allusers">allusersへ</Link>
      </div>
      <button onClick={logoutHandler}>
        <a href="/login">ログアウト</a>
      </button>
    </>
  );
};
