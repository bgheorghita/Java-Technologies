import React, { useState, useEffect } from 'react';

const TeacherPage = () => {
  const [newPreference, setNewPreference] = useState({
    username: '',
    content: '',
    registrationNumber: '',
  });
  const [submittedPreferences, setSubmittedPreferences] = useState([]);
  const token = localStorage.getItem('token');

  useEffect(() => {
    getAllPreferencesByLoggedUser();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewPreference((prevPreference) => ({
      ...prevPreference,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8090/preferences', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(newPreference),
      });

      if (!response.ok) {
        throw new Error(`Failed to save preference. Status: ${response.status}`);
      }

      console.log('Preference saved successfully!');
      setNewPreference({
        username: '',
        content: '',
        registrationNumber: '',
      });

      getAllPreferencesByLoggedUser();
    } catch (error) {
      console.error('Error saving preference:', error.message);
    }
  };

  const getAllPreferencesByLoggedUser = async () => {
    try {
      const response = await fetch('http://localhost:8090/preferences/user', {
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
      setSubmittedPreferences(data);
    } catch (error) {
      console.error('Error fetching preferences:', error.message);
    }
  };

  return (
    <div>
      <h2>Teacher Page</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Username:
          <input
            type="text"
            name="username"
            value={newPreference.username}
            onChange={handleChange}
          />
        </label>
        <br />
        <label>
          Content:
          <input
            type="text"
            name="content"
            value={newPreference.content}
            onChange={handleChange}
          />
        </label>
        <br />
        <label>
          Registration Number:
          <input
            type="text"
            name="registrationNumber"
            value={newPreference.registrationNumber}
            onChange={handleChange}
          />
        </label>
        <br />
        <button type="submit">Save Preference</button>
      </form>

      <h3>Submitted Preferences</h3>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Content</th>
            <th>Registration Number</th>
          </tr>
        </thead>
        <tbody>
          {submittedPreferences.map((preference) => (
            <tr key={preference.id}>
              <td>{preference.id}</td>
              <td>{preference.username}</td>
              <td>{preference.content}</td>
              <td>{preference.registrationNumber}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TeacherPage;
