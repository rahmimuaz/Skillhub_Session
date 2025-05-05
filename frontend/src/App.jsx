import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { GoogleOAuthProvider } from "@react-oauth/google";

import Navbar from "./components/Navbar";
import Login from "./pages/LoginPage/Login";
import Register from "./pages/LoginPage/Register";
import Users from "./pages/LoginPage/Users";
import Home from "./pages/HomePage/Home";

import CourseList from './components/CourseList';
import { CourseProvider } from './context/CourseContext';
import LearningProgress from './pages/LearningProgress/LearningProgress';

const App = () => {
  return (
    <GoogleOAuthProvider clientId="235074436580-fekrpapo667arbo0jkqa9nmprcpqul96.apps.googleusercontent.com">
      <CourseProvider>
       <Router>
        <Navbar />
          <Routes>
           <Route path="/" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/home" element={<Home />} />
            <Route path="/users" element={<Users />} />
            <Route path="/learning-progress" element={<LearningProgress />} />
            <Route path="/admin/courses" element={<CourseList />} />
          </Routes>
      </Router>
         </CourseProvider>
    </GoogleOAuthProvider>
  );
};

export default App;
