import React, { useState } from 'react';
import { Form, Input, Button } from 'antd';
import { addTransaction } from '../services/api';

const AddTransaction = ({ onAdd }) => {
  const [form] = Form.useForm();

  const onFinish = async (values) => {
    await addTransaction(values);
    form.resetFields();
    onAdd(); // 刷新列表
  };

  return (
    <Form form={form} onFinish={onFinish}>
      <Form.Item name="type" label="Transaction type" rules={[{ required: true }]}>
        <Input />
      </Form.Item>
      <Form.Item name="amount" label="amount" rules={[{ required: true }]}>
        <Input />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit">
          Add Transaction
        </Button>
      </Form.Item>
    </Form>
  );
};

export default AddTransaction;