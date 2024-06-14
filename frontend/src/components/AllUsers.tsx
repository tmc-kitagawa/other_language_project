import { useState, type FC, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './AllUsers.scss';

interface User {
  id: number;
  username: string;
}

export const AllUsers: FC = () => {
  const [allUsers, setAllUsers] = useState<User[] | null>(null);

  useEffect(() => {
    (async () => {
      const res: User[] = await axios('/api/users').then((res) => res.data);
      setAllUsers(res);
    })();
  }, []);

  //   // 開発用
  //   useEffect(() => {
  //     (async () => {
  //       const res: User[] = [
  //         { id: 1, username: 'userA' },
  //         { id: 2, username: 'u' },
  //         { id: 3, username: 'useruser' },
  //       ];
  //       setAllUsers(res);
  //     })();
  //   }, []);

  return (
    <>
      <div className="allusers-container">
        <h1>管理者用ページ</h1>
        {allUsers?.map((elem, i) => (
          <div className="user-container">
            <p key={i}>{elem.username}</p>
            <button
              onClick={async () => {
                await axios.delete(`/api/users/${elem.username}`);
                const res: User[] = await axios('/api/users').then(
                  (res) => res.data
                );
                setAllUsers(res);
              }}
            >
              <img src="/delete.svg" alt="削除" />
            </button>
          </div>
        ))}
      </div>
      <Link to="/">Homeへ</Link>
    </>
  );
};
