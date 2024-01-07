import React, { useState } from 'react';
import axios from 'axios';
import { jwtDecode } from "jwt-decode";

const LoginForm = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async () => {
    try {
      const response = await axios.get('http://localhost:8090/auth/login', {
        params: { username, password },
      });

      const token = response.data.token;

      localStorage.setItem('token', token);
      onLogin(token);

      const decoded = jwtDecode(token);
      if (decoded) {
        const roles = Array.isArray(decoded.groups) ? decoded.groups : [];
        console.log(roles)
        if(roles.includes('ADMIN')) {
          window.location.href = '/admin';
        } else if(roles.includes('TEACHER')) {
          window.location.href = '/teacher';
        } else {
          window.location.href = '/';
        }
      }

      console.log('Login successful');
      
    } catch (error) {
      console.error('Login failed:', error.response.data);
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={handleLogin}>Login</button>
    </div>
  );
};

export default LoginForm;
