from flask import Flask, jsonify
import os
import signal
import docker
    

app = Flask(__name__)

@app.route('/')
def index():
    return 'Hello World!'

@app.route('/list')
def list():
    list = []
    client = docker.from_env()
    containers = client.containers.list(all=True)
    return jsonify([d.name for d in containers])
            

@app.route('/stop_server')
def stop_server():
    os.kill(os.getpid(), signal.SIGKILL)
    return 'Server Stopped'

app.run(host='0.0.0.0', port=5000)