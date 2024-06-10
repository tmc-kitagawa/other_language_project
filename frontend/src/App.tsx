// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './App.css';
import { AllUsers } from './components/AllUsers';
import { Routes, Route } from 'react-router-dom';
import { Home } from './components/Home';

function App() {
  return (
    <>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="allusers" element={<AllUsers />} />
      </Routes>
    </>
  );
}

export default App;
