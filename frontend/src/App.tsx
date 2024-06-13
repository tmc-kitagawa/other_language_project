// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './App.scss';
import { AllUsers } from './components/AllUsers';
import { Routes, Route } from 'react-router-dom';
import { Home } from './components/Home';
import { Search } from './components/Search';

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="allusers" element={<AllUsers />} />
        <Route path="search" element={<Search />} />
      </Routes>
    </>
  );
}

export default App;
