import React from 'react';
import { Link } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";

const token = localStorage.getItem('token');
const isAuthenticated = !!token;
const decoded = token != null && jwtDecode(token);
const roles = decoded != null && Array.isArray(decoded.groups) ? decoded.groups : [];
const isTeacher = roles.includes('TEACHER');
const isAdmin = roles.includes('ADMIN');
    
const Navbar = () => {
  return (
    <nav>
      <ul>
        <li>
          <Link to="/">Home</Link>
        </li>
        {!isAuthenticated && (
          <>
            <li>
              <Link to="/login">Login</Link>
            </li>
            <li>
              <Link to="/register">Register</Link>
            </li>
          </>
        )}
        {isAuthenticated && (
          <>
            <li>
              <Link to="/logout">Logout</Link>
            </li>
          </>
        )}
        {isTeacher && (
          <>
            <li>
              <Link to="/teacher">Teacher page</Link>
            </li>
          </>
        )}
        {isAdmin && (
          <>
            <li>
              <Link to="/admin">Admin page</Link>
            </li>
          </>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;
