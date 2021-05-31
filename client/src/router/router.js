import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      alias: "/feedbacks",
      name: "feedbacks",
      component: () => import("../components/FeedbacksList")
    },
    {
      path: "/feedbacks/:id",
      name: "feedback-details",
      component: () => import("../components/Feedback")
    },
    {
      path: "/add",
      name: "add",
      component: () => import("../components/AddFeedback")
    }
  ]
});
