function Actor({ actorList }) {
  return (
    <>
    <ul>
      {actorList.forEach((actor) =>
        <li>{actor}</li>
      )} 
    </ul>
    </>
  )
}

export default Actor
