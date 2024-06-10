import type { FC } from 'react';
import { Link } from 'react-router-dom';

export const Home: FC = () => {
  return (
    <>
      <div>
        <p>Homeページ</p>
        <Link to="/allusers">allusersへ</Link>
      </div>
    </>
  );
};
