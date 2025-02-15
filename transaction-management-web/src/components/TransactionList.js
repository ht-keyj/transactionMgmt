import React, { useEffect, useState } from 'react';
import { Table, Button } from 'antd';
import { getTransactions, deleteTransaction } from '../services/api';

const TransactionList = () => {
  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    fetchTransactions();
  }, []);

  const fetchTransactions = async () => {
    const response = await getTransactions();
    setTransactions(response.data);
  };

  const handleDelete = async (id) => {
    await deleteTransaction(id);
    fetchTransactions(); // 刷新列表
  };

  const columns = [
    {
      title: 'Transaction type',
      dataIndex: 'type',

    },
    {
      title: 'Transaction amount',
      dataIndex: 'amount',

    },
    {
      title: 'Create Date',
      dataIndex: 'timestamp',

    },

    {
      title: 'Action',
      key: 'action',
      render: (_, record) => (
        <div>
          <Button type="link" onClick={() => {}}>
            edit
          </Button>
          <Button type="link" onClick={() => handleDelete(record.id)}>
            Delete
          </Button>
        </div>

      ),
    },
  ];

  return <Table dataSource={transactions} columns={columns} rowKey="id" />;
};

export default TransactionList;