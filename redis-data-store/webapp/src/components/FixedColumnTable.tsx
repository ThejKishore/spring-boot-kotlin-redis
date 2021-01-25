import React, {Component} from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import axios from 'axios'


class FixedColumnTable extends Component {

    state = {
        todos:[]
    }

    componentDidMount(){
       axios.get("http://jsonplaceholder.typicode.com/todos?_limit=5")
           .then(data =>{
               console.log(data.data)
               this.setState({todos:data.data})
           }).catch(err => console.error(err));
    }

     onSelectRowClick = (event:  { originalEvent: Event; data: any; index: number }) => {
        console.log("Selected Row",event.data);
    };

    render() {
        return (
            <div>
                <div className="card">
                    <DataTable value={this.state.todos} onRowClick={ (event) => this.onSelectRowClick(event)}>
                        <Column field="userId" header="UserId"></Column>
                        <Column field="id" header="Id"></Column>
                        <Column field="title" header="Title"></Column>
                        <Column field="completed" header="Completed" ></Column>
                    </DataTable>
                </div>
            </div>
        );
    }
}

export default FixedColumnTable;