#!/usr/bin/env ruby
require 'rubygems'
require 'eventmachine'

clients = 100
clients = ARGV.shift.to_i if ARGV.size > 0

HOST = 'localhost'
PORT = 5000

@@arr = ['zanmai', 'kazusuke', 'marutaka', 'homu', 'homuhomu']

class Client < EM::Connection
  def post_init
    EM::add_periodic_timer(1) do
      msg = @@arr.choice
      send_data msg+"\n"
    end
  end

  def receive_data(data)
    puts data
  end

  def unbind
    puts "connection closed - #{HOST}:#{PORT}"
  end
end

EM::run do
  clients.times do
    EM::connect(HOST, PORT, Client)
  end
end
