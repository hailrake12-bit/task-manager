import { useState } from "react";
import { useNavigate } from "react-router-dom";

function CreateList() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [items, setItems] = useState(["", "", ""]);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleItemChange = (index: number, value: string) => {
    const newItems = [...items];
    newItems[index] = value;
    setItems(newItems);
  };

  const addItem = () => {
    setItems([...items, ""]);
  };

  const removeItem = (index: number) => {
    const newItems = items.filter((_, i) => i !== index);
    setItems(newItems);
  };

  const handleSubmit = async () => {
    const body = {
      name,
      description,
      items: items
        .filter((item) => item.trim() !== "")
        .map((item) => ({ content: item })),
    };

    const response = await fetch("http://localhost:8081/list", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });

    if (response.ok) {
      navigate("/");
    } else {
      setMessage("Ошибка при создании");
    }
  };

  return (
    <div>
      <h2>Создать список</h2>

      <input
        type="text"
        placeholder="Название"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <input
        type="text"
        placeholder="Описание"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />

      <h3>Элементы списка</h3>
      {items.map((item, index) => (
        <div key={index}>
          <input
            type="text"
            placeholder={`Элемент ${index + 1}`}
            value={item}
            onChange={(e) => handleItemChange(index, e.target.value)}
          />
          <button onClick={() => removeItem(index)}>Удалить</button>
        </div>
      ))}

      <button onClick={addItem}>Добавить элемент</button>
      <br />
      <button onClick={handleSubmit}>Создать</button>

      {message && <p>{message}</p>}
    </div>
  );
}

export default CreateList;
