import { Routes, Route, Link } from "react-router-dom";
import CreateList from "./pages/CreateList";
import ListsList from "./pages/ListsList";
import "./App.css";

function App() {
  return (
    <div>
      <h1>Listify</h1>
      <nav>
        <Link to="/">Все списки</Link>
        {" | "}
        <Link to="/create">Создать список</Link>
      </nav>
      <Routes>
        <Route path="/" element={<ListsList />} />
        <Route path="/create" element={<CreateList />} />
      </Routes>
    </div>
  );
}

export default App;
