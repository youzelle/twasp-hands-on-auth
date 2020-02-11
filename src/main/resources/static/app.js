'use strict';

const {
    useState,
    useEffect,
} = React;

const buyTicket = (callback, ticket) => {
    fetch('/api/v0/buy', {
        method: 'POST',
        headers: {
            'content-type': 'application/json',
        },
        body: JSON.stringify(ticket)
    })
    .then(r => {
        if(r.ok){
            callback(ticket);
        }
    });
};

const BoughtTicket = ({destination, price}) => {
    return (<li>{destination} <em>Paid: {price} 💰</em></li>);
};
const AvailableTicket = ({destination, price, buyCallback}) => {
    return (<li>{destination}: <em>{price} 💰</em><button onClick={() => buyTicket(buyCallback, {destination, price})}>Buy</button></li>);
};

const App = () => {
    const [availableTickets, setAvailableTickets] = useState([]);
    const [myTickets, setMyTickets] = useState([]);
    const buyCallback = (newTicket) => {
        setMyTickets(myTickets.concat([newTicket]));
    };
    useEffect(() => {
        Promise.all([
            fetch('/api/v0/available-tickets')
                    .then(r => r.json()),
            fetch('/api/v0/my-tickets')
                    .then(r => r.json())
            ])
            .then(([availableTickets, myTickets]) => {
                setAvailableTickets(availableTickets);
                setMyTickets(myTickets);
            });
    }, []);
    return (
            <div>
                <h1>Hi Foo</h1>
                <h2>Here are the tickets you can buy</h2>
                <ul>
                    {availableTickets.map((ticket, index) => (<AvailableTicket key={index} destination={ticket.destination} price={ticket.price} buyCallback={buyCallback}/>))}
                </ul>
                <h2>Here are your tickets</h2>
                <ul>
                    {myTickets.map((ticket, index) => (<BoughtTicket key={index} destination={ticket.destination} price={ticket.price} />))}
                </ul>
            </div>);
}

const domContainer = document.querySelector('div.content');
ReactDOM.render((<App/>), domContainer);
