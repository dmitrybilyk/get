from flask import Flask, jsonify
import time

app = Flask(__name__)

@app.route("/logs/<int:id>")
def get_log(id):
    time.sleep(2)  # simulate slow API
    return jsonify({"log": f"Log message {id}"})

if __name__ == "__main__":
    app.run(port=5000)
