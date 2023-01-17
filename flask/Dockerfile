FROM python:3.9
WORKDIR /app
COPY main.py requirements.txt /app/
EXPOSE 5000
RUN pip install -r requirements.txt
CMD ["python3", "main.py"]