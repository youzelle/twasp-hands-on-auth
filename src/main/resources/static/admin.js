'use strict';

const {
  useState,
  useEffect,
} = React;

const updateFee = (callback, fee) => {
    fetch('/api/v0/admin/fee', {
        method: 'POST',
        headers: {
            'content-type': 'application/json',
        },
        body: JSON.stringify({amount: fee})
    })
    .then(r => {
        if(r.ok){
            callback(fee);
        }
    });
};

const AvailableTicket = ({destination, basePrice, fullPrice}) => {
    return (<li>{destination} | base price:<em>{basePrice} 💰</em>, with fee:<em>{fullPrice} 💰</em></li>);
};

const App = () => {
    const [availableTickets, setAvailableTickets] = useState([]);
    const [fee, setFee] = useState(0);
    useEffect(() => {
        Promise.all([
            fetch('/api/v0/admin/tickets')
                    .then(r => r.json()),
            fetch('/api/v0/admin/fee')
                    .then(r => r.json())
            ])
            .then(([availableTickets, fee]) => {
                setAvailableTickets(availableTickets);
                setFee(fee.amount);
            });
    }, []);
    const feeUpdateFn = (e) => {
        updateFee(setFee, +(e.target.parentElement.querySelector('input').value));
    };
    return (
            <div>
                <h1>Hi Admin</h1>
                <h2>The service fee for buying tickets is currently set to {fee}</h2>
                <div>
                    <input type="number" value={fee} onChange={feeUpdateFn}></input>
                    <button onClick={feeUpdateFn}>Update</button>
                </div>
                <h2>Here are the tickets listed on the website and their pricing</h2>
                <ul>
                    {availableTickets.map((ticket, index) => (<AvailableTicket key={index} destination={ticket.destination} basePrice={ticket.price} fullPrice={ticket.price + fee} />))}
                </ul>
            </div>);
}

const domContainer = document.querySelector('div.content');
ReactDOM.render((<App/>), domContainer);
