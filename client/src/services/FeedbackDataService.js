import api from "../api/feedback-api";

class FeedbackDataService {
    getAll() {
        return api.get("/feedbacks");
    }

    get(id) {
        return api.get(`/feedbacks/${id}`);
    }

    create(data) {
        return api.post("/feedbacks", data);
    }

    update(id, data) {
        return api.put(`/feedbacks/${id}`, data);
    }

    delete(id) {
        return api.delete(`/feedbacks/${id}`);
    }

    deleteAll() {
        return api.delete(`/feedbacks`);
    }
    
    findByTitle(title) {
        return api.get(`/feedbacks?title=${title}`);      
    }
}

export default new FeedbackDataService();