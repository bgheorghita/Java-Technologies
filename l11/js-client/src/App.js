import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './components/Home';
import LoginForm from './auth/LoginForm';
import RegistrationForm from './auth/RegistrationForm';
import AdminPage from './components/AdminPage';
import TeacherPage from './components/TeacherPage';
import Logout from './auth/Logout';

const App = () => {
  const [token, setToken] = useState(localStorage.getItem('token'));

  const handleLogin = (token) => {
    setToken(token);
  };

  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<LoginForm onLogin={handleLogin} />} />
        <Route path="/register" element={<RegistrationForm />} />
        <Route path="/admin" element={<AdminPage />} />
        <Route path="/teacher" element={<TeacherPage />} />
        <Route path="/logout" element={<Logout />} />
      </Routes>
    </Router>
  );
};

export default App;
