import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

export const getTransactions = () => axios.get(`${API_BASE_URL}/transactions`);
export const addTransaction = (transaction) => axios.post(`${API_BASE_URL}/transactions`, transaction);
export const updateTransaction = (id, transaction) => axios.put(`${API_BASE_URL}/transactions/${id}`, transaction);
export const deleteTransaction = (id) => axios.delete(`${API_BASE_URL}/transactions/${id}`);
