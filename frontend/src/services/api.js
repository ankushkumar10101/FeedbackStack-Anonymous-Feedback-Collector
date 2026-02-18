import axios from 'axios';

const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8000/api/',
    headers: {
        'Content-Type': 'application/json',
    },
});

export const pingBackend = async () => {
    try {
        await api.get('ping');
        console.log('Backend pinged successfully');
    } catch (error) {
        console.error('Error pinging backend:', error);
    }
};

export default api;
