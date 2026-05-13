import { useState, useEffect } from "react";

interface UserListItem {
  id: number;
  content: string;
}

interface UserList {
  id: number;
  name: string;
  description: string;
  createdAt: string;
  items: UserListItem[];
}

function ListsList() {
  const [lists, setLists] = useState<UserList[]>([]);
  const [openId, setOpenId] = useState<number | null>(null);

  useEffect(() => {
    fetch("http://localhost:8081/list")
      .then((res) => res.json())
      .then((data) => setLists(data));
  }, []);

  const toggleList = (id: number) => {
    setOpenId(openId === id ? null : id);
  };

  return (
    <div>
      <h2>Все списки</h2>
      {lists.map((list) => (
        <div key={list.id}>
          <h3>{list.name}</h3>
          <p>{list.description}</p>
          <button onClick={() => toggleList(list.id)}>
            {openId === list.id ? "Скрыть" : "Показать список"}
          </button>
          {openId === list.id && list.items && list.items.length > 0 && (
            <ul>
              {list.items.map((item) => (
                <li key={item.id}>{item.content}</li>
              ))}
            </ul>
          )}
          <small>{list.createdAt}</small>
        </div>
      ))}
    </div>
  );
}

export default ListsList;
