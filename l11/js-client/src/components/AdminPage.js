import React, { useState, useEffect } from 'react';

const AdminPage = () => {
  const [preferences, setPreferences] = useState([]);
  const [showTable, setShowTable] = useState(false);
  const token = localStorage.getItem('token');

  useEffect(() => {
    getAllPreferences();
  }, []);

  const getAllPreferences = async () => {
    try {
      const response = await fetch('http://localhost:8090/preferences', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error(`Failed to fetch preferences. Status: ${response.status}`);
      }

      const data = await response.json();
      setPreferences(data);
    } catch (error) {
      console.error('Error fetching preferences:', error.message);
    }
  };

  const handleDeletePreference = async (id) => {
    try {
      const response = await fetch(`http://localhost:8090/preferences/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error(`Failed to delete preference. Status: ${response.status}`);
      }

      getAllPreferences();
    } catch (error) {
      console.error('Error deleting preference:', error.message);
    }
  };

  const toggleShowTable = () => {
    setShowTable((prevShowTable) => !prevShowTable);
  };

  return (
    <div>
      <h2>Admin Page</h2>
      <button onClick={toggleShowTable}>
        {showTable ? 'Hide Preferences' : 'Show Preferences'}
      </button>

      {showTable && (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Content</th>
              <th>Registration Number</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {preferences.map((preference) => (
              <tr key={preference.id}>
                <td>{preference.id}</td>
                <td>{preference.username}</td>
                <td>{preference.content}</td>
                <td>{preference.registrationNumber}</td>
                <td>
                  <button onClick={() => handleDeletePreference(preference.id)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default AdminPage;
