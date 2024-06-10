import { useState, type FC, useEffect } from 'react';
import axios from 'axios';

interface User {
  id: number;
  name: string;
}

export const AllUsers: FC = () => {
  const [allUsers, setAllUsers] = useState<User[] | null>(null);

  useEffect(() => {
    (async () => {
      const res: User[] = await axios('/api/users').then((res) => res.data);
      setAllUsers(res);
    })();
  }, []);

  return (
    <>
      <div>
        <p>AllUsers</p>
        {allUsers?.map((elem, i) => (
          <p key={i}>{elem.name}</p>
        ))}
      </div>
    </>
  );
};
