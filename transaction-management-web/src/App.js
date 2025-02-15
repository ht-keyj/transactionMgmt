import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { Layout } from 'antd';
import Home from './pages/Home';

import './App.css';

const { Header, Content, Footer } = Layout;

const App = () => {
  return (
    <Router>
      <Layout>
        <Header style={{ color: 'white' }}>Transaction Management</Header>
        <Content style={{ padding: '24px' }}>
          <Routes>
            <Route path="/" element={<Home />} />
          </Routes>
        </Content>
        <Footer style={{ textAlign: 'center' }}>Transaction Management ©2024</Footer>
      </Layout>
    </Router>
  );
};

export default App;