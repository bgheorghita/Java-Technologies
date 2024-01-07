import React, { useEffect } from 'react';

const Logout = () => {
  useEffect(() => {
    localStorage.removeItem('token');
    window.location.href = '/';
  }, []);

  return (
    <div>
      <p>Logging out...</p>
    </div>
  );
};

export default Logout;
