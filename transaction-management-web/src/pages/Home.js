import React, { useState } from 'react';
import { Layout, Row, Col } from 'antd';
import TransactionList from '../components/TransactionList';
import AddTransaction from '../components/AddTransaction';


const { Content } = Layout;

const Home = () => {


  return (
    <Content style={{ padding: '24px' }}>
      <Row gutter={[16, 16]}>

        <Col span={24}>
          <AddTransaction onAdd={() => window.location.reload()} />
        </Col>
        <Col span={24}>
          <TransactionList />
        </Col>
      </Row>
    </Content>
  );
};

export default Home;